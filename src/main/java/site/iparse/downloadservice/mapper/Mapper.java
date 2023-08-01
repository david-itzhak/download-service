package site.iparse.downloadservice.mapper;

public interface Mapper<F, T> {

    T map(F from);

}
