package ua.estate.rialto.to.olx;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ParamsSearch {
    private Search search; // параметры поиска

    @Setter
    @Getter
    public class Search{
        private int district_id; // район
    }
}
