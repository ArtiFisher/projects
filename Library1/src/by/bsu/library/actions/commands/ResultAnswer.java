package by.bsu.library.actions.commands;

public class ResultAnswer {
    String page;
    boolean isForward;

    public ResultAnswer() {
        page = "";
        isForward = true;
    }

    public boolean isIsForward() {
        return isForward;
    }

    public void setIsForward(boolean isForward) {
        this.isForward = isForward;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
