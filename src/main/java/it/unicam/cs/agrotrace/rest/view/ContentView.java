package it.unicam.cs.agrotrace.rest.view;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ProductView.class, name = "product"),
        @JsonSubTypes.Type(value = ProcessView.class, name = "process"),
        @JsonSubTypes.Type(value = BundleView.class, name = "bundle")
})
public abstract class ContentView {
    protected String id;
    protected Long authorId;
    protected String title;
    protected String description;
    protected String validationStatus;
}
