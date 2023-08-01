package site.iparse.downloadservice.service.download.downloadServiceUtil;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EntryValueListOfMapToStringConverter {

    public Map<String, String> convertEntryValueListOfMapToString(Map<String, List<String>> initialMap, String delimiter) {
        Map<String, String> convertedMap = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : initialMap.entrySet()) {
            String entryKey = entry.getKey();
            List<String> valuesList = entry.getValue();

            // If there are multiple values, concatenate them with the specified delimiter
            String concatenatedValue = String.join(delimiter, valuesList);

            convertedMap.put(entryKey, concatenatedValue);
        }

        return convertedMap;
    }
}
