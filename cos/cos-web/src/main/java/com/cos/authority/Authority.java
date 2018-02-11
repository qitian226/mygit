package  com.cos.authority;

/**
 * 路径安全实体
 * @author qikunlun
 * @date   
 * 
 */
public class Authority {
private Boolean isURI=false;
private Boolean isFile=false;
private Boolean isPass=false;
/**
 * @return the isURI
 */
public Boolean getIsURI() {
	return isURI;
}
/**
 * @param isURI the isURI to set
 */
public void setIsURI(Boolean isURI) {
	this.isURI = isURI;
}
/**
 * @return the isFile
 */
public Boolean getIsFile() {
	return isFile;
}
/**
 * @param isFile the isFile to set
 */
public void setIsFile(Boolean isFile) {
	this.isFile = isFile;
}
/**
 * @return the isPass
 */
public Boolean getIsPass() {
	return isPass;
}
/**
 * @param isPass the isPass to set
 */
public void setIsPass(Boolean isPass) {
	this.isPass = isPass;
}

}
