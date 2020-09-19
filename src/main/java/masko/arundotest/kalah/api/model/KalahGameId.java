package masko.arundotest.kalah.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

/**
 * KalahGameId
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-16T19:43:18.338Z[GMT]")
public class KalahGameId   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("url")
  private String url = null;

  public KalahGameId() {
  }

  public KalahGameId(String id, String url) {
    this.id = id;
    this.url = url;
  }

  public KalahGameId id(String id) {
    this.id = id;
    return this;
  }

  @NotNull
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public KalahGameId url(String url) {
    this.url = url;
    return this;
  }

  @NotNull
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KalahGameId kalahGameId = (KalahGameId) o;
    return Objects.equals(this.id, kalahGameId.id) &&
        Objects.equals(this.url, kalahGameId.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, url);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KalahGameId {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
