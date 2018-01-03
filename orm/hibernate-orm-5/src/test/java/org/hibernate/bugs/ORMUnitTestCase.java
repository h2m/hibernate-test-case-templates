/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hibernate.bugs;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.bugs.entity.MyEntity;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using its built-in unit test framework.
 * Although ORMStandaloneTestCase is perfectly acceptable as a reproducer, usage of this class is much preferred.
 * Since we nearly always include a regression test with bug fixes, providing your reproducer using this method
 * simplifies the process.
 * <p>
 * What's even better?  Fork hibernate-orm itself, add your test case directly to a module's unit tests, then
 * submit it as a PR!
 */
public class ORMUnitTestCase extends BaseCoreFunctionalTestCase {

	private byte[] titleWorking = new byte[500]; // Sometimes it still worked with 1054 bytes
	private byte[] titleFailing = new byte[2000]; // Sometimes it already fails with 1055 bytes
	private byte[] description = new byte[10000];


	@Before
	public void initLongStrings() {
		Random random = new Random();
		random.nextBytes(description);
		random.nextBytes(titleWorking);
		random.nextBytes(titleFailing);
	}


	// Add your entities here.
	@Override
	protected Class[] getAnnotatedClasses() {
		return new Class[]{
		};
	}

	// If you use *.hbm.xml mappings, instead of annotations, add the mappings here.
	@Override
	protected String[] getMappings() {
		return new String[]{
				"MyEntity.hbm.xml"
		};
	}

	// If those mappings reside somewhere other than resources/org/hibernate/test, change this.
	@Override
	protected String getBaseForMappings() {
		return "";
	}

	// Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
	@Override
	protected void configure(Configuration configuration) {
		super.configure(configuration);

		configuration.setProperty(AvailableSettings.SHOW_SQL, Boolean.TRUE.toString());
		configuration.setProperty(AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString());
		//configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, Boolean.TRUE.toString() );
	}

	// Add your tests, using standard JUnit.
	@Test
	public void hhh8382TestWorking() throws Exception {
		// BaseCoreFunctionalTestCase automatically creates the SessionFactory and provides the Session.
		Session s = openSession();
		Transaction tx = s.beginTransaction();
		Long id = (Long) session.save(new MyEntity(new String(description), new String(titleWorking)));
		tx.commit();
		s.close();
	}

	@Test
	public void hhh8382TestFailing() throws Exception {
		// BaseCoreFunctionalTestCase automatically creates the SessionFactory and provides the Session.
		Session s = openSession();
		Transaction tx = s.beginTransaction();
		Long id = (Long) session.save(new MyEntity(new String(description), new String(titleFailing)));
		tx.commit();
		s.close();
	}


}
