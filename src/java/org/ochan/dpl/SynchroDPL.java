/*
Ochan - image board/anonymous forum
Copyright (C) 2010  David Seymore

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
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
