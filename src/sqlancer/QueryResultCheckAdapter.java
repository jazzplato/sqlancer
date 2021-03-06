package sqlancer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

public class QueryResultCheckAdapter extends QueryAdapter {

    private final Consumer<ResultSet> rsChecker;

    public QueryResultCheckAdapter(String query, Consumer<ResultSet> rsChecker) {
        super(query);
        this.rsChecker = rsChecker;
    }

    @Override
    public boolean execute(GlobalState<?, ?> globalState) throws SQLException {
        try (Statement s = globalState.getConnection().createStatement()) {
            ResultSet rs = s.executeQuery(getQueryString());
            rsChecker.accept(rs);
            return true;
        } catch (Exception e) {
            checkException(e);
            return false;
        }
    }

}
