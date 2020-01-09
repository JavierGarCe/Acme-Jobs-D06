
package acme.features.authenticated.thread;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.threads.Thread;
import acme.entities.threads.UserThread;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedThreadRepository extends AbstractRepository {

	@Query("select t from Thread t where t.id = ?1")
	Thread findOneById(int id);

	@Query("select ut.thread from UserThread ut where ut.authenticated.id = ?1")
	Collection<Thread> findManyByAuthenticatedId(int id);

	@Query("select count(ut) from UserThread ut where ut.authenticated.id = ?1 and ut.thread.id = ?2")
	Integer countNumberUserThreadByAuthenticatedIdAndThreadId(int autId, int threadId);

	@Query("select a from Authenticated a where a.id = ?1")
	Authenticated findAuthenticatedById(int id);

	@Query("select ut from UserThread ut where ut.thread.id=?1 and ut.authenticated.id =?2")
	UserThread findOneByThreadIdAndAuthenticatedId(int threadId, int authenticatedId);
}
