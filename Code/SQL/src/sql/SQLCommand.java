package sql;

import java.sql.Connection;

public interface SQLCommand {

	void splitCommand(SQLCommand command);
	void execute(Connection conn);
}
