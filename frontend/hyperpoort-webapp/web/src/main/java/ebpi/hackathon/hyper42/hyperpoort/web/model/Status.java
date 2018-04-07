package ebpi.hackathon.hyper42.hyperpoort.web.model;

import java.util.ArrayList;
import java.util.List;

public class Status {

	private String kenmerk;
	private List<Integer> statussen;

	public Status() {
		// TODO Auto-generated constructor stub
	}

	private Status(Builder builder) {
		this.kenmerk = builder.kenmerk;
		this.statussen = builder.statussen;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String kenmerk;
		private List<Integer> statussen;

		public Builder setKenmerk(String kenmerk) {
			this.kenmerk = kenmerk;
			return this;
		}

		public Builder setStatussen(List<Integer> statussen) {
			this.statussen = statussen;
			return this;
		}

		public Builder addStatussen(List<Integer> statussenElements) {
			if (statussenElements == null) {
				throw new IllegalArgumentException("statussenElements is null");
			}
			if (this.statussen == null) {
				this.statussen = new ArrayList<Integer>();
			}
			this.statussen.addAll(statussenElements);
			return this;
		}

		public Builder addStatussen(Integer statussenElement) {
			if (this.statussen == null) {
				this.statussen = new ArrayList<Integer>();
			}
			this.statussen.add(statussenElement);
			return this;
		}

		public Status build() {
			return new Status(this);
		}
	}

}
