import { Component, OnInit, OnDestroy } from '@angular/core';
import { NiggleService } from '../../../entities/niggle/niggle.service';
import { Niggle } from '../../../entities/niggle/niggle.model';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Subscription } from 'rxjs/Subscription';

@Component({
  selector: 'jhi-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {

  niggles: Niggle[];
  eventSubscriber: Subscription;
  constructor(
    private niggleService: NiggleService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
  ) { }

  ngOnInit() {
    this.loadNiggles();
    this.registerChangeInNiggles();
  }

  loadNiggles() {
    this.niggleService.query().subscribe(
      (res: HttpResponse<Niggle[]>) => {
        this.niggles = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: Niggle) {
    return item.id;
  }
  registerChangeInNiggles() {
    this.eventSubscriber = this.eventManager.subscribe('niggleListModification', (response) => this.loadNiggles());
  }

  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }
}
