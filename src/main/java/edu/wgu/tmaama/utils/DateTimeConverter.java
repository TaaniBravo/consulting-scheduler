package edu.wgu.tmaama.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DateTimeConverter is a business layer class that helps with three time conversions needed to be
 * made for this scheduler. It contains the system's local time, UTC time for the database, and EST
 * time for company business hours.
 */
public class DateTimeConverter {
  public static final String DATE_FORMAT =
      "yyyy-MM-dd HH:mm[:ss[.[SSSSSSSSS][SSSSSSSS][SSSSSSS][SSSSSS][SSSSS][SSSS][SSS][SS][S]]]";
  public static final String DISPLAY_FORMAT = "yyyy-MM-dd HH:mm";
  private final LocalDateTime localDateTime;
  private final ZonedDateTime utcDateTime;
  private final ZonedDateTime estDateTime;

  /**
   * Takes in a LocalDateTime instance and gets UTC time and then EST time.
   *
   * @param localDateTime - The system's local time.
   */
  public DateTimeConverter(LocalDateTime localDateTime) {
    this.localDateTime = localDateTime;
    this.utcDateTime =
        this.localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
    this.estDateTime =
        this.localDateTime
            .atZone(ZoneId.systemDefault())
            .withZoneSameInstant(ZoneId.of("America/New_York"));
  }

  public DateTimeConverter(Timestamp timestamp) {
    this.utcDateTime = timestamp.toLocalDateTime().atZone(ZoneOffset.UTC);
    this.localDateTime =
        this.utcDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    this.estDateTime = this.utcDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));
  }

  /**
   * @param time - The time in string format.
   * @param utc - If the string should be based on UTC time or local time.
   */
  public DateTimeConverter(String time, boolean utc) {
    if (utc) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT + " z");
      this.utcDateTime = ZonedDateTime.parse(time + " UTC", formatter);
      this.localDateTime = this.utcDateTime.toLocalDateTime();
    } else {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
      this.localDateTime = LocalDateTime.parse(time, formatter);
      this.utcDateTime =
          this.localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
    }
    this.estDateTime = this.localDateTime.atZone(ZoneId.of("America/New_York"));
  }

  /**
   * Get the localDateTime field.
   *
   * @return LocalDateTime
   */
  public LocalDateTime getLocalDateTime() {
    return localDateTime;
  }

  /**
   * Get the utcDateTime field.
   *
   * @return ZonedDateTime
   */
  public ZonedDateTime getUtcDateTime() {
    return utcDateTime;
  }

  /**
   * Get the estDateTime field.
   *
   * @return ZonedDateTime
   */
  public ZonedDateTime getEstDateTime() {
    return estDateTime;
  }

  public String getUTCString() {
    return utcDateTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
  }
}
