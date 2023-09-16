package Fitness.Fitness.Utilities;

public class CurrentUser {
    private static CurrentUser instance;
    private Long userId;
    private boolean isAdmin;

    private CurrentUser() {
    }

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
