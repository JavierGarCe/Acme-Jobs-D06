
package acme.features.authenticated.userThread;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.threads.Thread;
import acme.entities.threads.UserThread;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedUsersThreadRepository extends AbstractRepository {

	@Query("select t from Thread t where t.id = ?1")
	Thread findOneById(int id);

	@Query("select ut from UserThread ut where ut.thread.id = ?1")
	Collection<UserThread> findUserThreadsInThread(int threadId);

	@Query("select ut from UserThread ut where ut.id = ?1")
	UserThread findOneUserThreadById(int id);

	@Query("select ut.thread from UserThread ut where ut.id = ?1")
	Thread findThreadByUserThreadId(int id);

	@Query("select a from Authenticated a where a.id = ?1")
	Authenticated findOneAuthenticatedById(int id);

	@Query("select ut from UserThread ut where ut.thread.id=?1 and ut.authenticated.id =?2")
	UserThread findOneByThreadIdAndAuthenticatedId(int threadId, int authenticatedId);

	@Query("select a from Authenticated a where a not in (select ut.authenticated from UserThread ut where ut.thread.id = ?1)")
	Collection<Authenticated> findUserThreadNotInThread(int threadId);

}
