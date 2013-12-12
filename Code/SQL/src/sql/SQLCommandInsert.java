package sql;

import java.sql.Connection;

public class SQLCommandInsert implements SQLCommand{

	/* ATRIBUTES */
	
	public SQLCommandInsert(/* PARAMETERS */){
		/* MAKE SOMETHING HERE */
	}
	
	@Override
	public void splitCommand(SQLCommand command /* MORE PARAMETERS */) {
		/* MAKE SOMETHING HERE */
	}

	@Override
	public void execute(Connection conn /* MORE PARAMETERS */) {
		/* MAKE SOMETHING HERE */
	}

	/* Every command should be the same. We have to think about how this is going to be
	 * evry command have to have different thing each other and the common things
	 * should be in the interface  */
}
