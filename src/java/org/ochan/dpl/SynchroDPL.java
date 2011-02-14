package org.ochan.dpl;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

@Entity
public class SynchroDPL {

		@PrimaryKey(sequence="SYNCRHO")
		private Long identifier;

		/**
		 * @return the identifier
		 */
		public Long getIdentifier() {
			return identifier;
		}

		/**
		 * @param identifier the identifier to set
		 */
		public void setIdentifier(Long identifier) {
			this.identifier = identifier;
		}

		
		
}
