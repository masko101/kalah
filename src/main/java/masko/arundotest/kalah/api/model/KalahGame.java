package masko.arundotest.kalah.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * KalahGame
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-09-16T19:43:18.338Z[GMT]")
public class KalahGame   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("url")
  private String url = null;

  @JsonProperty("status")
  @Valid
  private List<Integer> status = new ArrayList<Integer>();

  public KalahGame id(String id) {
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

  public KalahGame url(String url) {
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

  public KalahGame status(List<Integer> status) {
    this.status = status;
    return this;
  }

  public KalahGame addStatusItem(Integer statusItem) {
    this.status.add(statusItem);
    return this;
  }

  @NotNull
  public List<Integer> getStatus() {
    return status;
  }

  public void setStatus(List<Integer> status) {
    this.status = status;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KalahGame kalahGame = (KalahGame) o;
    return Objects.equals(this.id, kalahGame.id) &&
        Objects.equals(this.url, kalahGame.url) &&
        Objects.equals(this.status, kalahGame.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, url, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KalahGame {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
