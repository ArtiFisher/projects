package by.epam.library.actions;

import by.epam.library.actions.commands.*;
import by.epam.library.actions.commands.librarian.*;
import by.epam.library.actions.commands.reader.*;

public enum CommandEnum {

    AUTHORIZATION {
        {
            this.command = new AuthorizationCommand();
        }
    },
    REGISTRATION {
        {
            this.command = new MoveToRegistration();
        }
    },
    REGISTER {
        {
            this.command = new RedirectRegistration();
        }
    },
    SHOWREG {
        {
            this.command = new Registration();
        }
    },
    ADMIN_VIEW_LIST_OF_BOOKS {
        {
            this.command = new ViewBooksAdmin();
        }
    },
    ADD_BOOK {
        {
            this.command = new MoveToAddingBook();
        }
    },
    ADD_BOOK_FILLING_INFORMATION {
        {
            this.command = new RedirectAddingBook();
        }
    },
    SHOWBOOKADDED {
        {
            this.command = new AddingBook();
        }
    },
    RETURN_TO_ADMIN_PAGE {
        {
            this.command = new MoveToAdminPage();
        }
    },
    REMOVE_BOOK_FROM_LIBARARY {
        {
            this.command = new MoveToRemovingBook();
        }
    },
    UPDATE_DB_AFTER_REMOVING {
        {
            this.command = new RemoveBookFromLibrary();
        }
    },
    VIEW_CLIENTS {
        {
            this.command = new ViewClients();
        }
    },
    DELETE_CLIENT {
        {
            this.command = new MoveToDeletingClient();
        }
    },
    DELETE_CLIENT_FROM_DB {
        {
            this.command = new DeleteClient();
        }
    },
    ADMIN_VIEW_CLIENT_CARD {
        {
            this.command = new ViewClientCardAdmin();
        }
    },
    LOG_OUT {
        {
            this.command = new LogOut();
        }
    },
    TAKE_BOOK {
        {
            this.command = new MoveToTakingBook();
        }
    },
    UPDATE_DB_AFTER_TAKING_BOOK {
        {
            this.command = new TakeBook();
        }
    },
    RETURN_BOOK {
        {
            this.command = new MoveToReturningBook();
        }
    },
    RETURN_TO_USER_PAGE {
        {
            this.command = new MoveToUserPage();
        }
    },
    RETURNBOOK {
        {
            this.command = new ReturnBook();
        }
    },
    UPDATE_DB_AFTER_RETURNING_BOOK {
        {
            this.command = new RedirectReturnBook();
        }
    },
    RU {
        {
            this.command = new SetLocaleRU();
        }
    },
    EN {
        {
            this.command = new SetLocaleEN();
        }
    };
    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
