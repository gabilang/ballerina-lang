public function function1() {
    int var1 = 10;
    int var2 = 20;
    int total = var1 + var2;
}

# This is function2
public function function2() {
    int var1 = 10;
    int var2 = 20;
    int total = var1 + var2;
}

# This is function3 with input parameters
#
# + param1 - param1 Parameter Description 
# + param2 - param2 Parameter Description
# + param3 - param3 Parameter Description
# + return - Return Value Description
public function function3(int param1, int param2, float... param3) returns int {
    int var1 = param1;
    int var2 = param2;
    int total = var1 + var2;
    
    return total;
}

# This is function3 with input parameters
#
# + param1 - param1 Parameter Description 
# + param2 - param2 Parameter Description
# + param3 - param3 Parameter Description
# + param4 - param4 Parameter Description
public function function4(int param1, int param2, string param3 = "test", float... param4) {
    int var1 = param1;
    int var2 = param2;
    int total = var1 + var2;
}

# This is function3 with input parameters
#
# + param1 - param1 Parameter Description 
# + param2 - param2 Parameter Description
function function5(int param1, int param2) {
    int var1 = param1;
    int var2 = param2;
    int total = var1 + var2;
}

# This is function6 with input parameters and return type
#
# + param1 - param1 Parameter Description 
# + param2 - param2 Parameter Description
# + return - Return Value Description
function function6(int param1, int param2) returns TestRecord3 {
    int var1 = param1;
    int var2 = param2;
    int total = var1 + var2;
    TestRecord3 rec = {};
    
    return rec;
}
