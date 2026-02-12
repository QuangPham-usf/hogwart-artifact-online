package edu.usf.cs.hogwart_artifact_online.system;

public class Result {

        private boolean flag; // true for success, false for error
        private Integer code; // e.g., 200 for Success, 404 for Not Found
        private String message; // Description of the result
        private Object data; // The actual artifact data (can be any type)

        // Default constructor
        public Result() {}

        // Constructor for all fields
        public Result(boolean flag, Integer code, String message, Object data) {
            this.flag = flag;
            this.code = code;
            this.message = message;
            this.data = data;
        }
    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

        // Getters and Setters
        public boolean isFlag() { return flag; }
        public void setFlag(boolean flag) { this.flag = flag; }

        public Integer getCode() { return code; }
        public void setCode(Integer code) { this.code = code; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public Object getData() { return data; }
        public void setData(Object data) { this.data = data; }

}
