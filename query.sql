#### SQL ####

#** Q1 **#
# WHERE Param ?1 and ?2 are Date intervals, e.g '2017-01-01 13:00:00.000' to '2017-01-01 14:00:00.000
# AND ?3 is Integer request threshold, e.g 100
select al.ip_address from parser.access_log al where al.date between ?1 and ?2 group by al.ip_address having count(al.ip_address) >= ?3;


#** Q2 **#
# WHERE param ?1 is a given String represation of an IP Address, e.g '192.168.11.231'
select al.* from parser.access_log al where al.ip_address = ?4 ;