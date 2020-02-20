package usf.sdlc.form;

public class Stock {
    private Quote quote;
    private Stats stats;

    public Stock() {
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
}
