package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEarningsCommand;
import seedu.address.logic.commands.ChangeTabCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteCustomCommand;
import seedu.address.logic.commands.DeleteEarningsCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEarningsCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewCommand;
import seedu.address.logic.commands.UnknownCommand;
import seedu.address.logic.commands.UpdateEarningsCommand;
import seedu.address.logic.commands.calendar.AddTaskCommand;
import seedu.address.logic.commands.calendar.DeleteTaskCommand;
import seedu.address.logic.commands.calendar.ListTasksCommand;
import seedu.address.logic.commands.note.AddNotesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commands.CommandObject;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static TreeMap<String, String> commandList;

    public AddressBookParser(ObservableList<CommandObject> commands) {
        AddressBookParser.commandList = new TreeMap<>();
        initialiseBasicCommands();
        for (int i = 0; i < commands.size(); i++) {
            AddressBookParser.commandList.put(commands.get(i).getCommandWord().word,
                    commands.get(i).getCommandAction().action);
        }
    }

    public AddressBookParser() {
        AddressBookParser.commandList = new TreeMap<>();
        initialiseBasicCommands();
    }

    public static TreeMap<String, String> getCommandList() {
        return AddressBookParser.commandList;
    }

    /**
     * Used to map basic commands to {@code TreeMap} when the AddressBookParser object is initialised.
     */
    private void initialiseBasicCommands() {
        AddressBookParser.commandList.put(AddCommand.COMMAND_WORD, AddCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(EditCommand.COMMAND_WORD, EditCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(ClearCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(DeleteCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(ListCommand.COMMAND_WORD, ListCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(FindCommand.COMMAND_WORD, FindCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(ExitCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(AddEarningsCommand.COMMAND_WORD, AddEarningsCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(DeleteCustomCommand.COMMAND_WORD, DeleteCustomCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(AddTaskCommand.COMMAND_WORD, AddTaskCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(DeleteEarningsCommand.COMMAND_WORD, DeleteEarningsCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(UpdateEarningsCommand.COMMAND_WORD, UpdateEarningsCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(FindEarningsCommand.COMMAND_WORD, FindEarningsCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(DeleteTaskCommand.COMMAND_WORD, DeleteTaskCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(ListTasksCommand.COMMAND_WORD, ListTasksCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(ChangeTabCommand.COMMAND_WORD, ChangeTabCommand.COMMAND_WORD);
        AddressBookParser.commandList.put(AddNotesCommand.COMMAND_WORD, AddNotesCommand.COMMAND_WORD);
    }

    /**
     * Used to check if the command a user wants to map an unknown command to exists.
     * Returns a {@code NewCommand} if the command exists or an {@code UnknownCommand} if it does not.
     */
    public Command checkCommand(String userInput, String prevUnknownCommand) {
        if (AddressBookParser.commandList.containsKey(userInput)) {
            AddressBookParser.commandList.put(prevUnknownCommand, AddressBookParser.commandList.get(userInput));
            return new NewCommand(AddressBookParser.commandList.get(userInput), prevUnknownCommand);
        } else {
            return new UnknownCommand(userInput);
        }
    }
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }


        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (commandList.containsKey(commandWord)) {
            switch (commandList.get(commandWord)) {
            case AddCommand.COMMAND_WORD:
                return new AddCommandParser().parse(arguments);

            case EditCommand.COMMAND_WORD:
                return new EditCommandParser().parse(arguments);

            case DeleteCommand.COMMAND_WORD:
                return new DeleteCommandParser().parse(arguments);

            case ClearCommand.COMMAND_WORD:
                AddressBookParser.commandList.clear();
                initialiseBasicCommands();
                return new ClearCommand();

            case FindCommand.COMMAND_WORD:
                return new FindCommandParser().parse(arguments);

            case ListCommand.COMMAND_WORD:
                return new ListCommand();

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            case AddEarningsCommand.COMMAND_WORD:
                return new AddEarningsCommandParser().parse(arguments);

            case DeleteCustomCommand.COMMAND_WORD:
                return new DeleteCustomCommandParser().parse(arguments);

            case ChangeTabCommand.COMMAND_WORD:
                return new ChangeTabCommandParser().parse(arguments);

            case AddTaskCommand.COMMAND_WORD:
                return new AddTaskCommandParser().parse(arguments);

            case DeleteTaskCommand.COMMAND_WORD:
                return new DeleteTaskCommandParser().parse(arguments);

            case ListTasksCommand.COMMAND_WORD:
                return new ListTasksCommand();

            case UpdateEarningsCommand.COMMAND_WORD:
                return new UpdateEarningsCommandParser().parse(arguments);

            case DeleteEarningsCommand.COMMAND_WORD:
                return new DeleteEarningsCommandParser().parse(arguments);

            case FindEarningsCommand.COMMAND_WORD:
                return new FindEarningsCommandParser().parse(arguments);

            case AddNotesCommand.COMMAND_WORD:
                return new AddNotesCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        } else {
            return new UnknownCommand(commandWord);
        }

    }

}
