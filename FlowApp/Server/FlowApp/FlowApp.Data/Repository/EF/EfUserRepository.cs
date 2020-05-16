using FlowApp.Core.Data.EF;
using FlowApp.Data.Abstract;
using FlowApp.Entity.Models;

namespace FlowApp.Data.Repository.EF
{
    public class EfUserRepository : EfEntityRepository<User, EFContext>, IUserRepository { }
}
