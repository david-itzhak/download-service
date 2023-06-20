package site.iparse.downloadservice.service;

import site.iparse.downloadservice.dto.ConnectionData;
import site.iparse.downloadservice.dto.ResponseData;

public interface DownloadService {

    ResponseData getResponseData(ConnectionData connectionData);
}
