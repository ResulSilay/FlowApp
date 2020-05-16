using FlowApp.Core.Data;
using FlowApp.Entity.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace FlowApp.Data.Abstract
{
    public interface IAuthRepository : IEntityRepository<User> { }
}