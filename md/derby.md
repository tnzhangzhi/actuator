java org.apache.derby.tools.ij
ij version 10.11
ij> connect 'jdbc:derby:testdb;create=true;user=tquist';
ij> -- the Database Owner must be the first user you create
call SYSCS_UTIL.SYSCS_CREATE_USER( 'tquist', 'tquist' );
0 rows inserted/updated/deleted
ij> -- now add other users
call SYSCS_UTIL.SYSCS_CREATE_USER( 'thardy', 'thardy' );
0 rows inserted/updated/deleted
ij> call SYSCS_UTIL.SYSCS_CREATE_USER( 'jhallett', 'jhallett' );
0 rows inserted/updated/deleted
ij> call SYSCS_UTIL.SYSCS_CREATE_USER( 'mchrysta', 'mchrysta' );
0 rows inserted/updated/deleted
ij> -- shut down the database in order to turn on NATIVE authentication
connect 'jdbc:derby:testdb;shutdown=true';
ERROR 08006: Database 'testdb' shutdown.
ij> -- these connection attempts fail because of bad credentials
connect 'jdbc:derby:testdb;user=tquist';
ERROR 08004: Connection authentication failure occurred.  Reason: Invalid authentication..
ij> connect 'jdbc:derby:testdb;user=thardy;password=tquist';
ERROR 08004: Connection authentication failure occurred.  Reason: Invalid authentication..
ij> -- these connection attempts present good credentials, so they succeed
connect 'jdbc:derby:testdb;user=tquist;password=tquist';
ij(CONNECTION1)> connect 'jdbc:derby:testdb;user=thardy;password=thardy';
ij(CONNECTION2)> connect 'jdbc:derby:testdb;user=jhallett;password=jhallett';

添加用户名密码：
connect 'jdbc:derby://localhost:1527/vovo;create=true;user=vovo';
call SYSCS_UTIL.SYSCS_CREATE_USER( 'vovo', 'qaz123456' );
connect 'jdbc:derby://localhost:1527/vovo;shutdown=true';
connect 'jdbc:derby://localhost:1527/vovo;user=vovo;password=qaz123456';
