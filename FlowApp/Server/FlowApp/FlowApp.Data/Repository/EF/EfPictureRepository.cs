using FlowApp.Data.Abstract;
using Microsoft.EntityFrameworkCore;
using System.Threading.Tasks;
using FlowApp.Core.Data.EF;
using FlowApp.Data.Repository.EF;
using FlowApp.Entity.Models;

namespace FlowApp.Data.Repository.EF
{
    public class EfPictureRepository : EfEntityRepository<Picture, EFContext>, IPictureRepository { }
}
