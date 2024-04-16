package app.dtos;

public class ScoreRequestDTO {

	private String resumeText;

	public ScoreRequestDTO(String resumeText) {
		super();
		this.resumeText = resumeText;
	}

	public String getResumeText() {
		return resumeText;
	}

	public void setResumeText(String resumeText) {
		this.resumeText = resumeText;
	}
	
	
}
