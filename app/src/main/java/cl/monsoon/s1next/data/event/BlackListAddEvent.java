package cl.monsoon.s1next.data.event;

public final class BlackListAddEvent {

    private final String authorPostId;
    private final String authorPostName;
    private final boolean isAdd;

    public BlackListAddEvent(String quotePostId, String quotePostCount, boolean isHide) {
        this.authorPostId = quotePostId;
        this.authorPostName = quotePostCount;
        this.isAdd = isHide;
    }

    public String getAuthorPostId() {
        return authorPostId;
    }

    public String getAuthorPostName() {
        return authorPostName;
    }

    public boolean isAdd() {
        return isAdd;
    }
}