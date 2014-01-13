package by.epam.library.actions.commands;

public class ResultAnswer {
    String page;
    boolean goToPage;

    public ResultAnswer() {
        page = "";
        goToPage = true;
    }

    public boolean isGoToPage() {
        return goToPage;
    }

    public void setGoToPage(boolean isForward) {
        this.goToPage = isForward;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
