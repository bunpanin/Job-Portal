package job_portal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResouceHanderConfig implements WebMvcConfigurer {

    @Value("${file-server-seeker-path}")
    private String seekerPath;

    @Override
    public void  addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/file/**").addResourceLocations("file:"+pathServer);
        registry.addResourceHandler("/seeker/images/**")
                .addResourceLocations("file:" + seekerPath);
    }
}
