## LocalDate、LocalDateTime 日期时间格式参数接收

@RequestParam 和 Post Json 的参数处理方式不一样，前者没有经过 Jackson 参与，所以两种情况单独处理。

情况1：@requestParam 提交的单个参数

### 方案1

```
spring.mvc.format.date = yyyy-MM-dd
spring.mvc.format.date-time = yyyy-MM-dd HH:mm:ss
spring.mvc.format.time = HH:mm:ss

```

### 方案2

```java
@Configuration
public class DateConverterConfig {

    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return source -> LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return source -> LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
```

### 方案3

```java
@ControllerAdvice
public class DateFormatConfig {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        });
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        });
        binder.registerCustomEditor(LocalTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        });
    }
}
```

情况2：Json 提交的参数

### 方案1

此 demo 略，请参考 Jackson 配置相关文章。

或者直接查看 JacksonConfig.kt 的代码。