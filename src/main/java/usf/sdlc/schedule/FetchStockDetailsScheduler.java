package usf.sdlc.schedule;

import io.micronaut.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Singleton;
import java.text.SimpleDateFormat;
import java.util.Date;


@Singleton
public class FetchStockDetailsScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(FetchStockDetailsScheduler.class);

    //@Scheduled(cron = "0 30 4 1/1 * ?") //Every night 04:30AM.....
//    @Scheduled(fixedDelay = "10s")
    void executeEveryTen() {
        System.out.println("Alper.Scheduled.Job.10....");
        LOG.info("Simple Job every 10 seconds: {}", new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));

        /**
         * TODO:
         * Do whatever you wanna do...
         */

    }

}
