package org.fishFromSanDiego.lab1.repositories.implementations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.fishFromSanDiego.lab1.abstractions.DepositChargeStrategy;
import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.models.*;
import org.fishFromSanDiego.lab1.repositories.abstractions.AccountRepository;

import java.math.BigDecimal;
import java.util.*;

public class NonPersistentAccountRepository implements AccountRepository {
    Map<CompoundKey, Account> _accounts;
    Map<CompoundKey, BigDecimal> _percents;
    ArrayList<FetchedModel<Transaction>> _transactions;

    public NonPersistentAccountRepository() {
        _accounts = new HashMap<>();
        _percents = new HashMap<>();
        _transactions = new ArrayList<FetchedModel<Transaction>>();
    }

    @Override
    public Optional<FetchedModel<Account>> findAccountById(int accountId, int clientId, int bankId) {
        CompoundKey key = new CompoundKey(bankId, accountId);
        if (!_accounts.containsKey(key)) return Optional.empty();
        Account account = _accounts.get(key);
        if (account.clientId() != clientId) return Optional.empty();
        return Optional.of(new FetchedModel<>(account, key.accountId));
    }

    @Override
    public Collection<FetchedModel<Account>> getAllClientAccounts(int clientId, int bankId) {
        return _accounts.entrySet().stream()
                .filter(e -> e.getKey().bankId == bankId && e.getValue().clientId() == clientId)
                .map(e -> new FetchedModel<>(e.getValue(), e.getKey().accountId))
                .toList();
    }

    @Override
    public void depositMoney(int accountId, int bankId, BigDecimal amount, boolean canBeReverted) throws RepositoryException {

        Optional<Map.Entry<CompoundKey, Account>> accountEntry = _accounts.entrySet().stream()
                .filter(e -> e.getKey().bankId == bankId && e.getKey().accountId == accountId)
                .findFirst();


        if (accountEntry.isEmpty()) {
            throw new RepositoryException("Couldn't find an account");
        }
        _accounts.put(accountEntry.get().getKey(),
                accountEntry.get().getValue()
                        .directBuilder(
                                Account.builder()).balance(accountEntry.get().getValue().balance().add(amount))
                        .build());
        _transactions.add(new FetchedModel<Transaction>(
                Transaction.Deposit.builder()
                        .recipientId(accountId)
                        .recipientBankId(bankId)
                        .canBeReverted(canBeReverted)
                        .sum(amount)
                        .build(),
                _transactions.size()));
    }

    @Override
    public void withdrawMoney(int accountId, int bankId, BigDecimal amount, boolean canBeReverted) throws RepositoryException {
        Optional<Map.Entry<CompoundKey, Account>> accountEntry = _accounts.entrySet().stream()
                .filter(e -> e.getKey().bankId == bankId && e.getKey().accountId == accountId)
                .findFirst();
        if (accountEntry.isEmpty()) {
            throw new RepositoryException("Couldn't find an account");
        }
        _accounts.put(accountEntry.get().getKey(),
                accountEntry.get().getValue()
                        .directBuilder(
                                Account.builder()).balance(accountEntry.get().getValue().balance().subtract(amount))
                        .build());
        _transactions.add(new FetchedModel<Transaction>(
                Transaction.Withdrawal.builder()
                        .accountId(accountId)
                        .bankId(bankId)
                        .canBeReverted(canBeReverted)
                        .sum(amount)
                        .build(),
                _transactions.size()));
    }

    @Override
    public void transferMoney(
            int senderAccountId,
            int senderBankId,
            int recipientAccountId,
            int recipientBankId,
            BigDecimal amount,
            boolean canBeReverted) throws RepositoryException {
        Optional<Map.Entry<CompoundKey, Account>> senderAccountEntry = _accounts.entrySet().stream()
                .filter(e -> e.getKey().bankId == senderBankId && e.getKey().accountId == senderAccountId)
                .findFirst();
        if (senderAccountEntry.isEmpty()) {
            throw new RepositoryException("Couldn't find sender's account");
        }
        Optional<Map.Entry<CompoundKey, Account>> recipientAccountEntry = _accounts.entrySet().stream()
                .filter(e -> e.getKey().bankId == recipientBankId && e.getKey().accountId == recipientAccountId)
                .findFirst();
        if (recipientAccountEntry.isEmpty()) {
            throw new RepositoryException("Couldn't find recipient's account");
        }
        _accounts.put(senderAccountEntry.get().getKey(),
                senderAccountEntry.get().getValue()
                        .directBuilder(
                                Account.builder()).balance(senderAccountEntry.get().getValue().balance().subtract(amount))
                        .build());
        _accounts.put(recipientAccountEntry.get().getKey(),
                recipientAccountEntry.get().getValue()
                        .directBuilder(
                                Account.builder()).balance(recipientAccountEntry.get().getValue().balance().add(amount))
                        .build());
        _transactions.add(new FetchedModel<Transaction>(
                Transaction.Transfer.builder()
                        .senderBankId(senderBankId)
                        .senderId(senderAccountId)
                        .recipientBankId(recipientBankId)
                        .recipientId(recipientAccountId)
                        .canBeReverted(canBeReverted)
                        .sum(amount)
                        .build(),
                _transactions.size()));
    }

    @Override
    public Collection<FetchedModel<Transaction>> getAllAccountTransactions(int accountId, int bankId) {
        return _transactions.stream()
                .filter(transaction -> transaction.value() instanceof Transaction.Deposit dep && dep.getRecipientId() == accountId
                        && dep.getRecipientBankId() == bankId
                        || transaction.value() instanceof Transaction.Withdrawal wth && wth.getAccountId() == accountId
                        && wth.getBankId() == bankId
                        || transaction.value() instanceof Transaction.Transfer tr && (tr.getRecipientId() == accountId
                        && tr.getRecipientBankId() == bankId || tr.getSenderId() == accountId
                        && tr.getSenderBankId() == bankId))
                .toList();
    }

    @Override
    public Collection<FetchedModel<Transaction>> getAllBankTransactions(int bankId) {
        return _transactions.stream()
                .filter(transaction -> transaction.value() instanceof Transaction.Deposit dep
                        && dep.getRecipientBankId() == bankId
                        || transaction.value() instanceof Transaction.Withdrawal wth
                        && wth.getBankId() == bankId
                        || transaction.value() instanceof Transaction.Transfer tr
                        && (tr.getRecipientBankId() == bankId
                        || tr.getSenderBankId() == bankId))
                .toList();

    }

    @Override
    public boolean tryRevertTransaction(int transactionId) throws RepositoryException {
        Optional<FetchedModel<Transaction>> transactionOptional = _transactions.stream().filter(tr -> tr.id() == transactionId)
                .findFirst();
        if (transactionOptional.isEmpty()) {
            throw new RepositoryException("Couldn't find transaction with such id");
        }
        var transaction = transactionOptional.get().value();
        if (!transaction.isCanBeReverted())
            return false;
        if (transaction instanceof Transaction.Deposit dep)
            withdrawMoney(dep.getRecipientId(), dep.getRecipientBankId(), dep.getSum(), false);
        if (transaction instanceof Transaction.Withdrawal wth)
            depositMoney(wth.getAccountId(), wth.getBankId(), wth.getSum(), false);
        if (transaction instanceof Transaction.Transfer transfer)
            transferMoney(
                    transfer.getSenderId(), transfer.getSenderBankId(),
                    transfer.getRecipientId(), transfer.getRecipientBankId(),
                    transfer.getSum(), false);
        return true;
    }

    @Override
    public void addNewAccount(int bankId, Account account) throws RepositoryException {
        _accounts.put(
                new CompoundKey(bankId, ((int) _accounts.entrySet().stream().filter(e -> e.getKey().bankId == bankId).count()))
                , account);
    }

    @Override
    public void chargeAllPercents(int bankId, BigDecimal debitCardPercent, DepositChargeStrategy depositChargeStrategy) {
        for (var entry : _accounts.entrySet()) {
            var accountBalance = entry.getValue().balance();
            _percents.putIfAbsent(entry.getKey(), BigDecimal.ZERO);
            if (entry.getValue().accountType() instanceof AccountType.Debit)
                _percents.
                        put(entry.getKey(),
                                accountBalance.multiply(BigDecimal.ONE.add(debitCardPercent)).add(_percents.get(entry.getKey())));
            if (entry.getValue().accountType() instanceof AccountType.Deposit) {
                _percents
                        .put(entry.getKey(),
                                accountBalance.multiply(BigDecimal.ONE.add(depositChargeStrategy.getPercentByBalance(accountBalance))).add(_percents.get(entry.getKey())));

            }
        }
    }

    @Override
    public void payAllPercents(int bankId) {
        for (var entry : _percents.entrySet()) {
            var account = _accounts.get(entry.getKey());
            _accounts.put(entry.getKey(), account.directBuilder(Account.builder()).balance(account.balance().add(entry.getValue())).build());
        }
    }

    @Override
    public void takeCommissions(int bankId, BigDecimal commissionSum) {
        var creditCardAccountEntrySets = _accounts.entrySet().stream()
                .filter(e -> e.getValue().accountType() instanceof AccountType.Credit)
                .toList();
        for (var entry : creditCardAccountEntrySets) {
            _accounts.
                    put(entry.getKey(),
                            entry.getValue().directBuilder(Account.builder()).balance(entry.getValue().balance().subtract(commissionSum)).build());
        }
    }


    private record CompoundKey(int bankId, int accountId) {
    }

}
