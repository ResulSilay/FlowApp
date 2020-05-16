using System.Threading.Tasks;
using FlowApp.Entity.Models;

namespace FlowApp.Business.Abstract
{
    public interface IAuthService
    {
        User Register(User user);
        User Login(User user);
        Task<bool> Any(string email);
    }
}
