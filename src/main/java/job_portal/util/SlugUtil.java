package job_portal.util;

public class SlugUtil {
    public static String toSlug(String slugName){
        if(slugName == null || slugName.isEmpty()){
            return "";
        }
        // Convert to lowercase
        String slug = slugName.toLowerCase();
        // Replace non-letter or non-digit characters with hyphen
        slug = slug.replaceAll("[^a-z0-9]+", "-");
        // Remove leading/trailing hyphens
        slug = slug.replaceAll("^-+|-+$", "");

        return slug;
    }
}
