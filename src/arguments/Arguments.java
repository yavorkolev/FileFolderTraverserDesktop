package arguments;

import traversing.FilteredTraverse;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.*;

public class Arguments {
    File mFileArg;
    private String mFile = null;
    private Integer mThreadsNumber = 1;
    private String mFilter = "no_filter";
    private String mCommand = "no_command";
    private String mLogFile = "no_logfile";
    Map<String, String> mArgsMap = new HashMap<>();
    String[] mArgs;
    public static List ARGUMENTS_KEYS = List.of(new String[]{"File", "Threads", "Filter", "Command", "LogFile"});

    public Arguments(String[] args) {
        mArgs = args;
    }

    public boolean argToMap() {
        boolean result = true;
        String key;
        String value;
        for (int i = 0; i < mArgs.length; i = i + 2) {
            if (ARGUMENTS_KEYS.contains(mArgs[i])) {
                key = mArgs[i];
                if (i + 1 < mArgs.length) {
                    value = mArgs[i + 1];
                } else {
                    value = null;
                }
                if (ARGUMENTS_KEYS.contains(value)) {
                    value = null;
                }
                mArgsMap.put(key, value);
            } else if (mArgs[i].contains(ARGUMENTS_KEYS.get(0).toString())) {
                System.out.println("Argument: " + mArgs[i] + " is not set properly!");
                result = false;
            } else if (mArgs[i].contains(ARGUMENTS_KEYS.get(1).toString())) {
                System.out.println("Argument: " + mArgs[i] + " is not set properly!");
                result = false;
            } else if (mArgs[i].contains(ARGUMENTS_KEYS.get(2).toString())) {
                System.out.println("Argument: " + mArgs[i] + " is not set properly!");
                result = false;
            } else if (mArgs[i].contains(ARGUMENTS_KEYS.get(3).toString())) {
                System.out.println("Argument: " + mArgs[i] + " is not set properly!");
                result = false;
            } else if (mArgs[i].contains(ARGUMENTS_KEYS.get(4).toString())) {
                System.out.println("Argument: " + mArgs[i] + " is not set properly!");
                result = false;
            }
        }
        if (result) {
            setArguments(mArgsMap);
        }
        return result;
    }

    private void setArguments(Map<String, String> mArgsMap) {
        for (Map.Entry<String, String> mapItem : mArgsMap.entrySet()) {

            if (ARGUMENTS_KEYS.contains(mapItem.getKey())) {
                if (ARGUMENTS_KEYS.get(0).equals(mapItem.getKey())) {
                    setFile(mapItem.getKey(), mapItem.getValue());
                } else if (ARGUMENTS_KEYS.get(1).equals(mapItem.getKey())) {
                    setThreadsNumber(mapItem.getKey(), mapItem.getValue());
                } else if (ARGUMENTS_KEYS.get(2).equals(mapItem.getKey())) {
                    setFilter(mapItem.getKey(), mapItem.getValue());
                } else if (ARGUMENTS_KEYS.get(3).equals(mapItem.getKey())) {
                    setCommands(mapItem.getKey(), mapItem.getValue());
                } else if (ARGUMENTS_KEYS.get(4).equals(mapItem.getKey())) {
                    setLogFile(mapItem.getKey(), mapItem.getValue());
                }
            }
        }
    }

    //getters and setters
    public String getFile() {
        return this.mFile;
    }

    public void setFile(String fileKey, String fileValue) {
        if (fileValue != null) {
            mFileArg = new File(fileValue);
            if (mFileArg.isDirectory()) {
                this.mFile = fileValue;
            } else {
                System.out.println("Value of argument \"" + fileKey + "\" is not folder!");
            }
        } else {
            System.out.println("Value of argument \"" + fileKey + "\" can't be empty!");
        }

    }

    public int getThreadsNumber() {
        return this.mThreadsNumber;
    }

    public void setThreadsNumber(String fileKey, String threadsValue) {
        if (threadsValue != null) {
            try {
                if (!threadsValue.equals("0")) {
                    this.mThreadsNumber = Integer.valueOf(threadsValue);
                } else {
                    System.out.println("Value of argument \"" + fileKey + "\" can't be \"0\"");
                    this.mThreadsNumber = 0;
                }
            } catch (Exception ex) {
                System.out.println("Value of argument \"" + fileKey + "\" is not set properly!");
                this.mThreadsNumber = 0;
            }
        }
    }

    public String getFilter() {
        return this.mFilter;
    }

    public void setFilter(String filterKey, String filterValue) {
        if (filterValue != null) {
            this.mFilter = filterValue;
        } else {
            System.out.println("Value of argument \"" + filterKey + "\" can't be empty!");
            this.mFilter = null;
        }
    }

    public String getCommand() {
        return this.mCommand;
    }

    public void setCommands(String commandKey, String commandValue) {
        if (commandValue != null) {
            if (FilteredTraverse.COMMANDS.contains(commandValue)) {
                this.mCommand = commandValue;
            } else {
                System.out.println("Value of argument \"" + commandKey + "\"  is not set properly!");
                this.mCommand = null;
            }
        } else {
            System.out.println("Value of argument \"" + commandKey + "\" can't be empty!");
            this.mCommand = null;
        }
    }

    public String getLogFile() {
        return this.mLogFile;
    }

    public void setLogFile(String logFileKey, String logFileValue) {
        if (logFileValue != null) {
            try {
                File parentLogfileFolder = new File(new File(logFileValue).getParent());
                if (!parentLogfileFolder.exists()) {
                    System.out.println("Value of argument \"" + logFileKey + "\" is not existing folder!");
                    this.mLogFile = null;
                } else {
                    this.mLogFile = logFileValue;
                }
            } catch (InvalidPathException | NullPointerException ex) {
                System.out.println("Value of argument \"" + logFileKey + "\" is not a folder!");
                this.mLogFile = null;
            }
        } else {
            System.out.println("Value of argument \"" + logFileKey + "\" can't be empty!");
            this.mLogFile = null;
        }
    }
}
