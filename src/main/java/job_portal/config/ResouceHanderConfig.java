package job_portal.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResouceHanderConfig implements WebMvcConfigurer {

    // private final FileServerSeekerProperties properties;
    // public ResouceHanderConfig(FileServerSeekerProperties properties) {
    //     this.properties = properties;
    // }
    @Value("${file-server-seeker-path}")
    private String seekerPath;

    @Override
    public void  addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/seeker/images/**")
                .addResourceLocations("file:" + seekerPath);
    }
}
