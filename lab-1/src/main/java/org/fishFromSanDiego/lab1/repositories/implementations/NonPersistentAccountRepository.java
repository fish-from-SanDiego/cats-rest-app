package org.fishFromSanDiego.lab1.repositories.implementations;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.fishFromSanDiego.lab1.exceptions.RepositoryException;
import org.fishFromSanDiego.lab1.models.*;
import org.fishFromSanDiego.lab1.repositories.abstractions.AccountRepository;

import java.math.BigDecimal;
import java.util.*;

public class NonPersistentAccountRepository implements AccountRepository {
    ArrayList<AccountInfo> _accountInfos;
    ArrayList<FetchedModel<Transaction>> _transactions;

    public NonPersistentAccountRepository() {
        _accountInfos = new ArrayList<AccountInfo>();
        _transactions = new ArrayList<FetchedModel<Transaction>>();
    }

    @Override
    public Optional<FetchedModel<Account>> findAccountById(int accountId, int clientId, int bankId) {
        return _accountInfos.stream()
                .filter(acc -> acc.accountId == accountId && acc.bankId == bankId && acc.account.clientId() == clientId)
                .map(acc -> new FetchedModel<Account>(acc.account, acc.accountId))
                .findFirst();
    }

    @Override
    public Collection<FetchedModel<Account>> getAllClientAccounts(int clientId, int bankId) {
        return _accountInfos.stream()
                .filter(acc -> acc.bankId == bankId && acc.account.clientId() == clientId)
                .map(acc -> new FetchedModel<Account>(acc.account, acc.accountId))
                .toList();
    }

    @Override
    public void depositMoney(int accountId, int bankId, BigDecimal amount) throws RepositoryException {
        Optional<AccountInfo> infoOptional = _accountInfos.stream()
                .filter(acc -> acc.accountId == accountId && acc.bankId == bankId)
                .findFirst();
        if (infoOptional.isEmpty()) {
            throw new RepositoryException("Couldn't find an account");
        }
        infoOptional.get().account =
                infoOptional.get().account
                        .directBuilder(Account.builder()).balance(infoOptional.get().account.balance().add(amount))
                        .build();
        _transactions.add(new FetchedModel<Transaction>(
                Transaction.Deposit.builder()
                        .recipientId(accountId)
                        .recipientBankId(bankId)
                        .isReverted(false)
                        .sum(amount)
                        .build(),
                _transactions.size()));
    }

    @Override
    public void withdrawMoney(int accountId, int bankId, BigDecimal amount) throws RepositoryException {
        Optional<AccountInfo> infoOptional = _accountInfos.stream()
                .filter(acc -> acc.accountId == accountId && acc.bankId == bankId)
                .findFirst();
        if (infoOptional.isEmpty()) {
            throw new RepositoryException("Couldn't find an account");
        }
        infoOptional.get().account =
                infoOptional.get().account
                        .directBuilder(Account.builder()).balance(infoOptional.get().account.balance().subtract(amount))
                        .build();
        _transactions.add(new FetchedModel<Transaction>(
                Transaction.Withdrawal.builder()
                        .accountId(accountId)
                        .bankId(bankId)
                        .isReverted(false)
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
            BigDecimal amount) throws RepositoryException {
        Optional<AccountInfo> senderInfoOptional = _accountInfos.stream()
                .filter(acc -> acc.accountId == senderAccountId && acc.bankId == senderBankId)
                .findFirst();
        if (senderInfoOptional.isEmpty()) {
            throw new RepositoryException("Couldn't find sender's account");
        }
        Optional<AccountInfo> recipientInfoOptional = _accountInfos.stream()
                .filter(acc -> acc.accountId == recipientAccountId && acc.bankId == recipientBankId)
                .findFirst();
        if (recipientInfoOptional.isEmpty()) {
            throw new RepositoryException("Couldn't find recipient's account");
        }
        senderInfoOptional.get().account =
                senderInfoOptional.get().account
                        .directBuilder(Account.builder()).balance(senderInfoOptional.get().account.balance().subtract(amount))
                        .build();
        recipientInfoOptional.get().account =
                recipientInfoOptional.get().account
                        .directBuilder(Account.builder()).balance(recipientInfoOptional.get().account.balance().add(amount))
                        .build();
        _transactions.add(new FetchedModel<Transaction>(
                Transaction.Transfer.builder()
                        .senderBankId(senderBankId)
                        .senderId(senderAccountId)
                        .recipientBankId(recipientBankId)
                        .recipientId(recipientAccountId)
                        .isReverted(false)
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
    public void addNewAccount(int bankId, Account account) throws RepositoryException {
        _accountInfos.add(new AccountInfo(bankId, _accountInfos.size(), account));
    }

    @Data
    @AllArgsConstructor
    private static class AccountInfo {
        int bankId;
        int accountId;
        Account account;
    }
}
