TUA_FUN_ACCS_CTL:isSatisfiedPercentageColumnsMatched=false
TUA_FUN:noWhereClauseFoundisSatisfiedPercentageColumnsMatched=false
--------------------------------------------------
delete from PRDDBS1.TUA_FUN_ACCS_CTL where ACCS_LVL=' ' and CAT_ID=' ' and FUN_ID=' '
delete from PRDDBS1.TUA_FUN where CAT_ID=' ' and FUN_ID=' ' and BUT_NUM=' '
--------------------------------------------------
select * from PRDDBS1.TUA_FUN_ACCS_CTL where cat_id like 'CSA%'
select * from PRDDBS1.TUA_FUN