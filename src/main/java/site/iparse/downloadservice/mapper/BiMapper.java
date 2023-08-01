package site.iparse.downloadservice.mapper;

public interface BiMapper<FF, FS, T> {

    T map(FF fromFirst, FS fromSecond);
}
