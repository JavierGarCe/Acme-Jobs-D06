
package acme.features.authenticated.message;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.customization.Customization;
import acme.entities.messages.Message;
import acme.entities.threads.Thread;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedMessageRepository extends AbstractRepository {

	@Query("select m from Message m where m.id = ?1")
	Message findOneById(int id);

	@Query("select t from Thread t where t.id = ?1")
	Thread findThreadById(int id);

	@Query("select t.id from Thread t join t.messages m where m.id = ?1")
	Integer findThreadIdByMessageId(int id);

	@Query("select count(ut) from UserThread ut where ut.authenticated.id = ?1 and ut.thread.id = ?2")
	Integer countNumberUserThreadByAuthenticatedIdAndThreadId(int autId, int threadId);

	@Query("select t from Thread t join t.messages m where m.id = ?1")
	Thread findThreadByMessageId(int id);
  
	@Query("select a from Authenticated a where a.userAccount.id = ?1 ")
	Authenticated findAuthenticated(int id);
  
	@Query("select t.messages from Thread t where t.id = ?1")
	Collection<Message> findMessages(int id);

	@Query("select c from Customization c")
	Customization getCustomization();

}
