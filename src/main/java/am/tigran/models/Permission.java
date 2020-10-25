package am.tigran.models;

public enum Permission {

    CREATE_TASK("create:task"),
    READ_TASK("read:task"),
    CHANGE_USER("change:user"),
    DELETE_TASK("delete:task"),
    CHANGE_STATUS("change:status"),
    CHANGE_ROLE("change:role");


    private final String permission;

    Permission(String permission) {

        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
