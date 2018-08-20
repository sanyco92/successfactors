package support;

/**
 * Created by aleksandr.kot on 8/14/18.
 */
public class Time {
    private String hours;
    private String minutes;
    private String timeFormat;
    private String timePeriod;
    private static final String[] timePeriods = {"AM", "PM"};

    public Time(String timeFormat) {
        this.timeFormat = timeFormat;
        if (timeFormat == "12h") {
            this.timePeriod = timePeriods[(int) (Math.random() * 2)];
            this.hours = String.valueOf((int) (Math.random() * 12 + 1));
            String minutesValue = String.valueOf((int) (Math.random() * 60));
            if (Integer.parseInt(minutesValue) < 10)
                this.minutes = "0" + minutesValue;
            else
                this.minutes = minutesValue;
        }
        else if (timeFormat == "24h") {
            String hoursValue = String.valueOf((int) (Math.random() * 24));
            String minutesValue = String.valueOf((int) (Math.random() * 60));
            if (Integer.parseInt(hoursValue) < 10) {
                this.hours = "0" + hoursValue;
            }
            else this.hours = hoursValue;
            if (Integer.parseInt(minutesValue) < 10) {
                this.minutes = "0" + minutesValue;
            }
            else this.minutes = minutesValue;
        }
        else System.out.println("Wrong time format!");
    }

    public String getHours() {
        return hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }
}
