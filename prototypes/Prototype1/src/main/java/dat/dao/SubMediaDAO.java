package dat.dao;

import dat.entities.SubMedia;

import java.util.HashMap;
import java.util.Map;

public class SubMediaDAO {

    private static SubMediaDAO INSTANCE;
    private static Map<String, SubMedia> subMedias = new HashMap<>();

    private SubMediaDAO() {}

    public static SubMediaDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SubMediaDAO();
        }

        return INSTANCE;
    }

    public void truncate() {
        subMedias.clear();
    }

    public void create(SubMedia subMedia) {
        subMedias.put(subMedia.getName(), subMedia);
    }

    public SubMedia read(String subMedia) {
        return subMedias.getOrDefault(subMedia, null);
    }
}