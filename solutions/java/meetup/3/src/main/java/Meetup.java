import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

class Meetup {

    private int monthOfYear;

    private int year;

    public int getMonthOfYear() {
        return monthOfYear;
    }

    public int getYear() {
        return year;
    }

    Meetup(int monthOfYear, int year) {
        try {
            if(monthOfYear > 12 || monthOfYear < 1 || year < 1900 || year > 2026) {
                throw new UnsupportedOperationException("Invalid input : month or year");
            }
            this.monthOfYear = monthOfYear;
            this.year = year;
        } catch (UnsupportedOperationException ex) {
//            System.out.println("Meetup cannot be created with the given entries: month - " + monthOfYear + " and year - " + year + " with exception : " + ex);
        } catch (Exception ex) {
//            System.out.println("With exception message : " + ex);
        }
    }

    LocalDate day(DayOfWeek dayOfWeek, MeetupSchedule schedule) {
        YearMonth yearMonth = YearMonth.of(getYear(), getMonthOfYear());
        LocalDate current = yearMonth.atDay(1);
        List<LocalDate> matchingDays = new LinkedList<>();

        while (current.getMonthValue() == getMonthOfYear()) {
            if (current.getDayOfWeek() == dayOfWeek) {
                matchingDays.add(current);
            }
            current = current.plusDays(1);
        }

        return switch (schedule) {
            case FIRST -> matchingDays.get(0);
            case SECOND ->
                // week = 2
                    matchingDays.get(1);
            case THIRD ->
                // week = 3
                    matchingDays.get(2);
            case FOURTH ->
                // week = 4
                    matchingDays.get(3);
            case LAST ->
                // week = 4
                    matchingDays.get(matchingDays.size() - 1);
            case TEENTH ->
                // week = x
                    matchingDays.stream().filter(date -> {
                        return date.getDayOfMonth() >= 13 && date.getDayOfMonth() <= 19;
                    }).findFirst().orElseThrow(IllegalStateException::new);
        };
    }

}