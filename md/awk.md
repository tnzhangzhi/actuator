awk -F: '/root/{print $1}' /etc/passwd 
搜索/etc/passwd有root关键字的所有行，并显示对应的名称

cat /etc/passwd |awk  -F ':'  '{print $1"\t"$7}'
如果只是显示/etc/passwd的账户和账户对应的shell,而账户与shell之间以tab键分割

https://www.cnblogs.com/ggjucheng/archive/2013/01/13/2858470.html