package ru.hip_spb.view;

import java.io.Writer;
import java.time.LocalDateTime;
import java.util.List;

import ru.hip_spb.model.Ensemble;
import ru.hip_spb.model.Performer;


public class ConcertView {

    private final Writer writer;
    private final String[] months = {
            "Января",
            "Февраля",
            "Марта",
            "Апреля",
            "Мая",
            "Июня",
            "Июля",
            "Августа",
            "Сентября",
            "Октября",
            "Ноября",
            "Декабря"
    };

    public ConcertView(Writer writer) {
        this.writer = writer;
    }

    public String format(
            String programName,
            LocalDateTime dateTime,
            List<Ensemble> ensembles,
            String place,
            String address,
            String program,
            String link,
            int number
    ) {

        StringBuilder result =
                new StringBuilder();
        result.append("<div class='concert_wrapper' onclick='expand(")
                .append(number)
                .append(")'>")
                .append("<div class='date_name_wrapper' id='dn_wrapper")
                .append(number)
                .append("'>")
                .append("<div class='day'>")
                .append(dateTime.getDayOfMonth())
                .append("</div>")
                .append("<div class='month_time_wrapper'>")
                .append("<div class='month'>")
                .append(months[dateTime.getMonthValue()])
                .append("</div>")
                .append("<div class='time'>")
                .append(String.format("%02d", dateTime.getHour()))
                .append(':')
                .append(String.format("%02d", dateTime.getMinute()))
                .append("</div></div>")
                .append("<div class='title' id='title")
                .append(number)
                .append("'>")
                .append(programName)
                .append("</div></div>")
                .append("<div class='details_wrapper' id='d_wrapper")
                .append(number)
                .append("'>");
        result.append("<div class='program'>")
                .append(program)
                .append("</div>");
        result.append("<div class='ensembles_wrapper'>");

        for (Ensemble ensemble : ensembles) {
            result.append("<div class='ensemble'>");
            if (!ensemble.getName().isEmpty()) {
                result.append(ensemble.getName())
                        .append(':');
            }
            result.append("<ul class='performers'>");
            for (Performer performer : ensemble.getPerformers()) {
                result.append("<li>")
                        .append(performer.getName())
                        .append(" - ");
                int i;
                for (i = 0; i < performer.getInstruments().size() - 1; i++) {

                    result.append(performer
                                    .getInstruments()
                                    .get(i)
                                    .getName())
                            .append(", ");
                }
                result.append(performer
                                .getInstruments()
                                .get(i)
                                .getName())
                        .append("</li>");
            }
            result.append("</ul>");
        }

        result.append("</div></div>");
        result.append("<div class='place'>")
                .append(place)
                .append(" (")
                .append(address)
                .append(')')
                .append("</div>");
        result.append("<div class='link'>")
                .append("<a href='")
                .append(link)
                .append("'>")
                .append(link)
                .append("</a>")
                .append("</div>");
        result.append("</div>");
        result.append("</div>");

        return result.toString();
    }
}
