package org.jumbo.simpletrace.splitter;

import com.google.common.collect.ImmutableList;
import org.jumbo.simpletrace.datastructures.CommandConversionException;
import org.jumbo.simpletrace.datastructures.CommandSection;
import org.jumbo.simpletrace.datastructures.CommandType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CurlToComponents {
    public static Map<CommandType, List<String>> extractComponents(String curl) throws CommandConversionException {
        Map<CommandType, List <String>> componentMap = new HashMap<>();
        curl = curl.replaceAll("\\R", " ");

        componentMap.computeIfAbsent(CommandType.HEADER, k -> new LinkedList<>());
        componentMap.computeIfAbsent(CommandType.REQUEST_TYPE, k -> new LinkedList<>());
        componentMap.computeIfAbsent(CommandType.BODY, k -> new LinkedList<>());
        componentMap.computeIfAbsent(CommandType.NONE, k -> new LinkedList<>());
        componentMap.computeIfAbsent(CommandType.ORIGINAL_COMMAND, k -> new LinkedList<>());


        componentMap.get(CommandType.ORIGINAL_COMMAND).add(curl);

        componentMap = extractComponents(validateAndRemoveCurlCommand(curl), componentMap);

        componentMap = addRequestTypeIfNonExistent(componentMap);



        return componentMap;
    }

    private static Map<CommandType, List <String>>  addRequestTypeIfNonExistent(Map<CommandType, List <String>> componentMap ){
        if(componentMap.get(CommandType.REQUEST_TYPE).size() == 0) {
            String requestType = "GET";
            if(componentMap.get(CommandType.BODY).size() > 0) {
                requestType = "POST";
            }
            componentMap.get(CommandType.REQUEST_TYPE).add(requestType);
        }
        return componentMap;

    }

    private static Map<CommandType, List <String>> extractComponents(String curl, Map<CommandType, List <String>> componentMap) throws CommandConversionException {
        CommandSection nextCommandSection = extractNextSection(curl);
        String remainingCurl = curl.substring(nextCommandSection.getNextStartingPoint()).trim();
        componentMap.get(nextCommandSection.getCommandType()).add(nextCommandSection.getCurrentString());

        if(remainingCurl.trim().length() <=0 ) {
            return componentMap;
        } else {
            return extractComponents(remainingCurl, componentMap);
        }
    }

    private static boolean isQuote(char charToCheck) {
        return charToCheck == '"' || charToCheck == '\'';
    }

    private static boolean isNextParamaterCommand(String nextParameter) {
        return nextParameter.charAt(0) == '-';
    }


    private static CommandSection extractNextSection(String curl) throws CommandConversionException {
        curl = curl.trim();
        if (!isNextParamaterCommand(curl)) {
            return extractPayload(curl);
        } else { // We know we are dealing with a command here
            String [] commandAndRemainingCurl = curl.split("\\s", 2);
            String command = commandAndRemainingCurl[0];
            String remainingCurl = commandAndRemainingCurl[1].trim();
            CommandType commandType = extractCommandType(command.trim());
            CommandSection commandSectionWithRemainingLengthNotIncludingCommand = extractPayload(remainingCurl);
            return new CommandSection(commandSectionWithRemainingLengthNotIncludingCommand.getCurrentString(),
                    commandSectionWithRemainingLengthNotIncludingCommand.getNextStartingPoint() + command.length() + 1,
                    commandType);
        }
    }

    private static CommandSection extractPayload(String curl) {
        char nextChar = curl.charAt(0);
        if (isQuote(nextChar)) {
            return new CommandSection(extractPayloadInQuote(curl, nextChar), CommandType.NONE);
        } else {
            return new CommandSection(extractPayloadWithoutQuotes(curl), CommandType.NONE);
        }
    }

    private static CommandType extractCommandType(String commandSection) {
        List dataOptions = ImmutableList.of("-d", "--data-raw", "--data");
        List headerOptions = ImmutableList.of("-H", "--header");
        List requestTypeOptions = ImmutableList.of("-X", "--request");

        if(dataOptions.contains(commandSection)) {
            return CommandType.BODY;
        } else if (headerOptions.contains(commandSection)) {
            return CommandType.HEADER;
        } else if (requestTypeOptions.contains(commandSection)) {
            return CommandType.REQUEST_TYPE;
        } else {
            return CommandType.NONE;
        }

    }

    private static CommandSection extractPayloadInQuote(String remainingCurl, char enclosingQuote) throws CommandConversionException {
        if(!isQuote(enclosingQuote)) {
            throw new CommandConversionException("Invalid quote, expected either ' or \", but instead got " + enclosingQuote + ", current remaining curl is: " + remainingCurl);
        }
        final int dataStart = 1;
        int dataEnd = remainingCurl.indexOf(enclosingQuote, dataStart);

        do {
            if(remainingCurl.charAt(dataEnd - 1) != '\\') {
                break;
            } else if (dataEnd > remainingCurl.length()) {
                throw new CommandConversionException("getPayloadInQuote hasn't found a viable quote");
            }  else {
                dataEnd = remainingCurl.indexOf(enclosingQuote, dataEnd + 1);
            }

        } while (true);

        return new CommandSection(remainingCurl.substring(dataStart, dataEnd), dataEnd + 1);
    }

    private static CommandSection extractPayloadWithoutQuotes(String curl) throws CommandConversionException {
        final int dataStart = 0;
        int dataEnd = curl.indexOf(" ", dataStart);
        if(dataEnd == -1) {
            dataEnd = curl.length();
        }
        return new CommandSection(curl.substring(dataStart, dataEnd), dataEnd);
    }

    private static String validateAndRemoveCurlCommand(String curl) {
        String curlRegex = "^curl ";
        curl = curl.trim();

        if(curl.matches(curlRegex + ".*")){
            return curl.replaceAll(curlRegex, "");
        } else {
            throw new RuntimeException("Not a valid curl command, must start with 'curl '");
        }
    }
}
