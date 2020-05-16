using FlowApp.Core.Data.EF;
using FlowApp.Data.Abstract;
using FlowApp.Entity.Models;

namespace FlowApp.Data.Repository.EF
{
    public class EfRateRepository : EfEntityRepository<Rating, EFContext>, IRateRepository { }
}
