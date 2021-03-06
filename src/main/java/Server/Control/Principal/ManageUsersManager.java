package Server.Control.Principal;

import Server.Control.Manager;
import Models.Account.Account;
import Models.Account.Principal;
import Models.Shop.Category.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ManageUsersManager extends Manager {

    private static List<Account> users;
    private static Sort currentSort;

    public ManageUsersManager(Account account) {
        super(account);
        this.users = Account.getAllAccounts();
    }

    public String showAvailableSorts() {
        return "name";
    }

    public boolean isEnteredSortFieldValid(String sort) {
        return sort.equals("name");
    }

    public static ArrayList<Object> sort(String sort, boolean isAscending) {
        users = Account.getAllAccounts();
        currentSort = new Sort(sort, isAscending);
        applySort();
        return new ArrayList<>(users);
    }

    public static ArrayList<String> getSortFields() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("name");
        return fields;
    }

    public String currentSort() {
        return currentSort.toString();
    }

    public ArrayList<Object> disableSort() {
        currentSort = null;
        users = Account.getAllAccounts();
        return new ArrayList<>(users);
    }

    private static void applySort() {
        if (currentSort == null) {
            return;
        }
        Account[] usersForSort = users.toArray(new Account[0]);
        for (int i = 0; i < usersForSort.length; i++) {
            for (int j = i + 1; j < usersForSort.length; j++) {
                if (usersForSort[i].getName().compareTo(usersForSort[j].getName()) > 0) {
                    Account temp = usersForSort[i];
                    usersForSort[i] = usersForSort[j];
                    usersForSort[j] = temp;
                }
            }
        }
        if (!currentSort.isAscending()) {
            Collections.reverse(users);
        }
        users = Arrays.asList(usersForSort);
    }

    public int deleteUsername(String username, String target) {
        if (!target.equals(username)) {
            Account.deleteAccount(Account.getAccountByUsername(target));
            return 0;
        }
        return 1;
    }

    public int createManagerProfile(ArrayList<String> inputs) {
        // username, password, email, phoneNumber, firstName, lastName
        if(Account.hasAccountWithUsername(inputs.get(0))) {
            new Principal(inputs.get(0), inputs.get(4), inputs.get(5), inputs.get(2), inputs.get(3), inputs.get(1));
            return 0;
        }
        return 1;
    }
}
