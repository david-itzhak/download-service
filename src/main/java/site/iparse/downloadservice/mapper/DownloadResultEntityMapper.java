package site.iparse.downloadservice.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.iparse.downloadservice.db.entity.DownloadResult;
import site.iparse.downloadservice.dto.ConnectionData;
import site.iparse.downloadservice.dto.ResponseData;
import site.iparse.downloadservice.service.download.downloadServiceUtil.MapToJsonConverter;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DownloadResultEntityMapper implements BiMapper<ConnectionData, ResponseData, DownloadResult>{

    private final MapToJsonConverter<String, String> mapToJsonConverter;
    @Override
    public DownloadResult map(ConnectionData fromFirstParam, ResponseData fromSecondParam) {
        return DownloadResult.builder()
                .taskUuid(fromFirstParam.getTaskUuid())
                .downloadUrl(fromFirstParam.getDownloadUrl())
                .downloadText(fromSecondParam.getHttpBody())
                .httpMethod(fromFirstParam.getHttpMethod())
                .requestHeaders(convertMapToJsonString(fromFirstParam.getHeaders(), fromFirstParam, fromSecondParam))
                .requestCookies(convertMapToJsonString(fromFirstParam.getCookies(), fromFirstParam, fromSecondParam))
                .proxyHost(fromFirstParam.getProxyHost())
                .proxyPort(fromFirstParam.getProxyPort())
                .proxyUsername(fromFirstParam.getProxyUsername())
                .proxyPassword(fromFirstParam.getProxyPassword())
                .downloadStatus(fromSecondParam.getStatusCode())
                .downloadStatusMessage(fromSecondParam.getStatusMessage())
                .responseContentType(fromSecondParam.getContentType())
                .responseHeaders(convertMapToJsonString(fromSecondParam.getHeaders(), fromFirstParam, fromSecondParam))
                .responseCookies(convertMapToJsonString(fromSecondParam.getCookies(), fromFirstParam, fromSecondParam))
                .responseTimestamp(fromSecondParam.getResponseTimestamp())
                .attemptCount(fromFirstParam.getAttemptCount())
                .build();
    }

    private String convertMapToJsonString(Map<String, String> map,
                                          ConnectionData connectionData,
                                          ResponseData responseData) {
        try {
            return mapToJsonConverter.convertToJson(map);
        } catch (JsonProcessingException e) {
            log.warn("Error while converting map to json string. \nConnectionData: {} \nResponseData: {} \n",
                    connectionData,
                    responseData);
            return null;
        }
    }
}
